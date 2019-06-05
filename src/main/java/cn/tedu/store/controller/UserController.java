package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.UploadAvatarException;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    // 自动装配 UserService
    @Autowired
    private IUserService userService;


    @RequestMapping(value = "/handle_reg.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Void> handleReg(@RequestParam("username") String username,
                                          @RequestParam("password") String password, String phone, String email,
                                          @RequestParam(value = "gender", required = false, defaultValue = "1") Integer gender) {
        // 将五个参数封装到user对象中
        User user = new User(username, password, email, phone, gender);
        // 调用业务层对象的相关方法
        userService.reg(user);
        return new ResponseResult<Void>();
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/handle_login.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Void> handleLogin(@RequestParam("username") String username,
                                            @RequestParam("password") String password, HttpSession session) {
        User user = userService.login(username, password);
        session.setAttribute("uid", user.getId());
        session.setAttribute("username", user.getUsername());
        return new ResponseResult<Void>();
    }

    @RequestMapping(value = "/handle_change_password.do", method = RequestMethod.POST)
    @ResponseBody
    // 修改密码
    public ResponseResult<Void> handleChangePassword(@RequestParam("old_password") String oldPassword,
                                                     @RequestParam("new_password") String newPassword, HttpSession session) {
        // 从Session对象获取绑定uid
        Integer uid = getUidFromSession(session);
        // 执行修改
        userService.changePasswordByOldPassword(uid, oldPassword, newPassword);
        return new ResponseResult<Void>();


    }

    /**
     * 修改用户数据
     *
     * @param user
     * @param session
     * @param avatarFile
     * @param request
     * @return
     */
    @RequestMapping(value = "handle_change_info.do")
    @ResponseBody
    public ResponseResult<String> handleChangeInfo(User user, HttpSession session,
                                                   @RequestParam CommonsMultipartFile avatarFile, HttpServletRequest request) {
        // 判断用户名和邮箱，如果为"",则设置为null
        if ("".equals(user.getUsername())) {
            user.setUsername(null);
        }
        if ("".equals(user.getEmail())) {
            user.setEmail(null);
        }
        // 判断此次操作是否上传了头像
        if (!avatarFile.isEmpty()) {
            // 上传头像并获取上传头像的路径
            String avatarPath = uploadAvatar(avatarFile, request);
            // 把头像文件的路径封装，以写入数据表中
            user.setAvatar(avatarPath);
        }

        Integer id = getUidFromSession(session);
        user.setId(id);
        userService.changeInfo(user);

        ResponseResult<String> rr = new ResponseResult<String>();
        rr.setData(user.getAvatar());
        return rr;
    }

    /**
     * 登录成功后获取用户信息
     *
     * @param session
     * @return
     */
    @RequestMapping("/getInfo.do")
    @ResponseBody
    public ResponseResult<User> getInfo(HttpSession session) {
        // 从session中获取uid
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        // 调用业务层的getUserById()，得到当前用户的User数据
        User user = userService.getUserById(uid);
        // 创建返回值对象，将User对象封装到Data属性中
        ResponseResult<User> rr = new ResponseResult<User>();
        rr.setData(user);
        // 返回
        return rr;
    }

    /**
     * @param avatarFile CommonsMultipartFile
     * @param request    HttpServletRequest
     * @return 成功上传后，返回图片储存的路径
     * @throws UploadAvatarException
     */
    private String uploadAvatar(@RequestParam CommonsMultipartFile avatarFile, HttpServletRequest request)
            throws UploadAvatarException {
        // 处理上传的图像
        // 确定文件保存到的文件夹的路径
        String uploadDirPath = request.getServletContext().getRealPath("upload");
        // 确定头像保存到的文件夹
        File uploadDir = new File(uploadDirPath);
        // 确保文件夹存在
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String suffix = avatarFile.getOriginalFilename().substring(avatarFile.getOriginalFilename().lastIndexOf("."));
        // 确定文件保存到哪个文件
        String fileName = UUID.randomUUID().toString() + suffix;
        File dest = new File(uploadDir, fileName);
        // 保存图像文件
        try {
            avatarFile.transferTo(dest);
            return "upload/" + fileName;
        } catch (IllegalStateException e) {
            throw new UploadAvatarException("上传头像失败，非法状态！！");
        } catch (IOException e) {
            throw new UploadAvatarException("上传头像失败，读写异常！！");
        }

    }

}
