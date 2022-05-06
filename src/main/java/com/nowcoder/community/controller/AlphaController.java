package com.nowcoder.community.controller;

import  org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @RequestMapping("/hello")
    @ResponseBody
    public  String savHello(){
        return "Hello Spring Boot.";
    }




    //简单测试一下请求响应，浏览器地址栏url输入http://localhost:8080/community/alpha/http?code=123&name=zqh
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求数据
        System.out.println(request.getMethod());//获取请求方式
        System.out.println(request.getServletPath());//获取请求资源路径，输出的是/alpha/http
        Enumeration<String> enumeration = request.getHeaderNames();//获取所有请求头，放入一个迭代器里
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code"));//获取地址栏url中请求参数的值(仅GET)

        // 设置响应数据
        response.setContentType("text/html;charset=utf-8");//设置响应体类型是html文本，字符编码是utf-8,所以浏览器会用utf-8去解析响应体
        try (
                PrintWriter writer = response.getWriter();//响应体的内容用流的方式去写
        ) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // (2)获取GET请求的请求参数的两种方式?
    // GET请求多用于浏览器向服务器获取数据

    // 方式1:
    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)//限定只能由GET请求来访问这个方法
    @ResponseBody//返回字符串或者json字符串的时候才加这个注解，如果加上这个注解，又返回java对象，比如下面的集合，那么这个集合对象会被转换为json字符串，如果返回html就不用加这个注解
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,//这个注解的作用是将地址栏中对应请求参数给这个方法的形参，但这个请求参数不是必须，如果没有这个请求参数，就用默认值给方法形参
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    //方式2:
    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)//直接把请求参数放到地址栏的路径里，然后用下面@PathVariable("id")注解获得路径中含有的请求参数，并传入方法形参
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }




    // (3)获取POST请求的请求参数的一种方式?
    // POST请求多用于浏览器向服务器提交数据，一般用表单提交
    // 访问表单的时候要在浏览器地址栏输入http://localhost:8080/community/html/student.html，注意不要写static目录或者直接从IDEA的浏览器打开html，否则会报错
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {//形参名和表单中的name属性的值一致就可以自动传入
        System.out.println(name);
        System.out.println(age);
        return "success";
    }




    // (4)服务器响应HTML数据的两种方式

    //方式1:ModelAndView类
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");//一般我们都是从业务层和数据访问层获得数据后再addObject到MAV对象里，现在就简单的单纯addObject一下就好
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");//templates不用写,设置view路径
        return mav;
    }

    //方式2:Model接口(推荐)
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "/demo/view";//返回给浏览器view.html,后缀不用写
    }




    // (5)服务器响应JSON数据的一种方式
    // (服务器常在异步请求的时候响应JSON数据到浏览器，比如注册账号时，你输入一个用户名，浏览器就请求服务器看这个用户名是否被注册，然后返回JSON数据告诉你是否注册过，但注册页面是没刷新的，这就叫异步请求)
    // 为什么需要JSON数据?
    // 因为我返回给浏览器Java对象，但浏览器无法将Java对象转换为JS对象，但是如果我先把Java转换为JSON字符串，浏览器就可以将JSON字符串转换为JS对象了

    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody//加了这个注解后，你返回的Map会被自动转换为JSON字符串给浏览器
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("salary", 9000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 25);
        emp.put("salary", 10000.00);
        list.add(emp);

        return list;
    }
}
