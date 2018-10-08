package com.teamface.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teamface.common.ResumeParse;
import com.teamface.common.Util;

/**
 * @Description:
 * @author: Administrator
 * @date: 2018年8月15日 下午6:56:23
 * @version: 1.0
 */
@Controller
@RequestMapping("/common")
public class CommonController
{
    static Logger log = LoggerFactory.getLogger(CommonController.class);
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody JSONObject login(@RequestBody(required = true) String reqJsonStr, HttpServletRequest request)
    {
        JSONObject resultJsonObject = new JSONObject();
        try
        {
            JSONObject requestJson;
            requestJson = JSONObject.parseObject(reqJsonStr);
            String userName = requestJson.getString("userName");
            String passWord = requestJson.getString("passWord");
            String url = requestJson.getString("url");
            log.info("userName:" + userName + ",password:" + passWord + ",url:" + url);
            
            int code = 404;
            String token = "no";
            JSONObject result = Util.getCompanyId(userName, passWord);
            if (result != null)
            {
                String employeeId = result.getString("employee_id");
                String companyId = result.getString("company_id");
                code = 200;
                token = employeeId.concat("-").concat(companyId);
            }
            resultJsonObject.put("code", code);
            resultJsonObject.put("token", token);
            /*
            JSONObject json = DAOUtil.executeQuery4FirstJSON("select * from account where id=14", null);
            System.out.println(json.toJSONString());
            DataSourceBeanBuilder dataSourceBeanBuilder = new DataSourceBeanBuilder("source-1", "192.168.1.173", "5432", "custom_test3", "hjhq", "hjhq123");
            DataSourceContext.setDataSource(dataSourceBeanBuilder);
            json = DAOUtil.executeQuery4FirstJSON("select * from account where id=14", null);
            System.out.println(json.toJSONString());
            DataSourceContext.clearDataSource();
            */
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
        return resultJsonObject;
    }
    
    @RequestMapping(value = "/saveResume", method = RequestMethod.POST)
    public @ResponseBody JSONObject saveResume(@RequestBody(required = true) String reqJsonStr, HttpServletRequest request)
    {
        
        JSONArray resultArray = new JSONArray();
        JSONObject resultJsonObject = new JSONObject();
        JSONObject json = JSONObject.parseObject(reqJsonStr);
        String html = json.getString("resumeInfo");
        String url = json.getString("resumeUrl");
        String type = json.getString("resumeType");
        String cookie = json.getString("cookie");
        String token = json.getString("token");
        String cmd = type;
        String[] tokenArr = token.split("-");
        if (tokenArr.length < 2)
        {
            resultJsonObject.put("cmd", "no");
            return resultJsonObject;
        }
        String employeeId = tokenArr[0];
        String companyId = tokenArr[1];
        if (url.contains("51job.com"))
        {
            ResumeParse.parse51jobHtml(url, html, employeeId, companyId);
        }
        else if (url.contains("zhipin.com"))
        {
            ResumeParse.parseZhipinHtml(url, html, employeeId, companyId);
        }
        else if (url.contains("lagou.com"))
        {
            ResumeParse.parseLagouHtml(url, html, cookie, employeeId, companyId);
        }
        else if (url.contains("ihr.zhaopin.com"))
        {
            ResumeParse.parseZhaopinHtml(url, html, employeeId, companyId);
        }
        else if (url.contains("rd5.zhaopin.com"))
        {
            ResumeParse.parseZhaopinRdHtml(url, html, employeeId, companyId);
        }
        else if (url.contains("cjol.com"))
        {
            ResumeParse.parseCjolHtml(url, html, employeeId, companyId);
        }
        else if (url.contains("h.liepin.com"))
        {
            ResumeParse.parseLptHHtml(url, html, employeeId, companyId);
        }
        else if (url.contains("lpt.liepin.com"))
        {
            ResumeParse.parseLptHtml(url, html, employeeId, companyId);
        }
        else if (url.contains("jxrcw.com"))
        {
            ResumeParse.parseJxrcwHtml(url, html, employeeId, companyId);
        }
        else if (url.contains("58.com"))
        {
            if ("users".equals(type))
            {
                ResumeParse.dataMap.clear();
                JSONObject tempJson = JSONObject.parseObject(html);
                JSONObject dataJson = tempJson.getJSONObject("data");
                int count = dataJson.getIntValue("count");
                int pageSize = dataJson.getIntValue("pageSize");
                int pageindex = dataJson.getIntValue("pageindex");
                if (pageSize > 0)
                {
                    int pages = (int)Math.ceil((float)count / pageSize);
                    int ycount = count % pageSize;
                    int alreadyCount = (pages - pageindex) * pageSize + ycount;
                    if (alreadyCount > pageSize)
                    {
                        if (url.contains("deliverlist"))
                        {
                            cmd = "download";
                        }
                        else
                        {
                            cmd = "cancel";
                        }
                    }
                    System.out.println(pages + ":" + ycount + ":" + alreadyCount);
                }
                // System.out.println(pageindex + ":" + count + ":" + pageSize);
                JSONArray array = dataJson.getJSONArray("resumeList");
                for (int i = 0; i < array.size(); i++)
                {
                    JSONObject obj = array.getJSONObject(i);
                    String rid = obj.getString("id");
                    ResumeParse.dataMap.put(rid, obj);
                }
            }
            else if ("reget".equals(type))
            {
                ResumeParse.dataMap.clear();
                cmd = type;
                JSONObject tempJson = JSONObject.parseObject(html);
                JSONObject dataJson = tempJson.getJSONObject("data");
                JSONArray array = dataJson.getJSONArray("resumeList");
                for (int i = 0; i < array.size(); i++)
                {
                    JSONObject obj = array.getJSONObject(i);
                    JSONObject itemJson = new JSONObject();
                    String rlink = obj.getString("url");
                    String rid = obj.getString("id");
                    String turl = rlink + "?tf_token=1&id=" + rid;
                    if (rlink.indexOf("?") > 0)
                    {
                        turl = rlink + "&tf_token=1&id=" + rid;
                    }
                    itemJson.put("url", turl);
                    resultArray.add(itemJson);
                    ResumeParse.dataMap.put(rid, obj);
                }
            }
            else
            {
                // System.out.println("cookie:" + type);
                ResumeParse.parse58Html(url, html, employeeId, companyId);
            }
        }
        else
        {
            cmd = "no";
        }
        resultJsonObject.put("data", resultArray);
        resultJsonObject.put("cmd", cmd);
        return resultJsonObject;
    }
}
