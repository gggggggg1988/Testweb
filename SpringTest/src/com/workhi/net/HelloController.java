package com.workhi.net;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/9 0009.
 */
@Controller
@RequestMapping(value = "/hello", method = RequestMethod.GET)
public class HelloController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        model.addAttribute("msg", "Spring MVC Hello World");
        model.addAttribute("name", "yuntao");
        return "hello";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/fileUpload",method = RequestMethod.GET)
    public String showUploadPage() {
        return "file/file";
    }

    /**
     * 图片文件上传
     */
      @ResponseBody
      @RequestMapping(value = "/photoUpload",method = RequestMethod.POST)
      public ResultData<Object> photoUpload(MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IllegalStateException, IOException {
                ResultData<Object> resultData=new ResultData<>();
                // 判断用户是否登录
                /*User user=(User) session.getAttribute("user");
      if (user==null) {
          resultData.setCode(40029);
          resultData.setMsg("用户未登录");
          return resultData;
      }*/
                 if (file!=null) {// 判断上传的文件是否为空
                    String path=null;// 文件路径
                    String type=null;// 文件类型
                    String fileName=file.getOriginalFilename();// 文件原名称
                    System.out.println("上传的文件原名称:"+fileName);
                    // 判断文件类型
                    type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
                    if (type!=null) {// 判断文件类型是否为空
                    if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                                   // 项目在容器中实际发布运行的根路径
                                   String realPath=request.getSession().getServletContext().getRealPath("/");
                                   // 自定义的文件名称
                                   String trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
                                   // 设置存放图片文件的路径
                                   path=realPath+/*System.getProperty("file.separator")+*/trueFileName;
                                   System.out.println("存放图片文件的路径:"+path);
                                   // 转存文件到指定的路径
                                   file.transferTo(new File(path));
                                   System.out.println("文件成功上传到指定目录下");
                               }else {
                                   System.out.println("不是我们想要的文件类型,请按要求重新上传");
                                   return null;
                               }
                       }else {
                           System.out.println("文件类型为空");
                           return null;
                       }
                }else {
                    System.out.println("没有找到相对应的文件");
                    return null;
                }
            return resultData;
        }
}