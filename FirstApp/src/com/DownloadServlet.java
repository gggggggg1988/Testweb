package com;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Administrator on 2017/9/17 0017.
 */
public class DownloadServlet extends HttpServlet {

//�ַ�����

        private final String ENCODING="GB2312";

//��������

        private final String CONTENT_TYPE="text/html;charset=gb2312";

//Ҫ���ص��ļ���ŵ�·��

        private String downloadfiledir="d:\\temp\\";

        public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{

//����request������ַ�����

            request.setCharacterEncoding(ENCODING);

//��request��ȡ��Ҫ�����ļ�������

            String filename=request.getParameter("filename");

            if(filename==null||filename.trim().equals("")){

//����response�����ContentType

                response.setContentType(CONTENT_TYPE);

//���������Ϣ

                PrintWriter out=response.getWriter();

                out.println("<font color=red>������ļ�����Ч��</font>");

                out.close();

            }else{

//�����ļ�������·����

                String fullfilename=downloadfiledir+filename;

                System.out.println("�����ļ���"+fullfilename);

//�����ļ�����������response�����ContentType

                String contentType=getServletContext().getMimeType(fullfilename);

                if(contentType==null)

                    contentType="application/octet-stream";

                response.setContentType(contentType);

//����response��ͷ��Ϣ

                response.setHeader("Content-disposition","attachment;filename=\""+filename+"\"");

                InputStream is=null;

                OutputStream os=null;

                try{

                    is=new BufferedInputStream(new FileInputStream(fullfilename));

//��������ֽ���

                    ByteArrayOutputStream baos=new ByteArrayOutputStream();

//����response�������

                    os=new BufferedOutputStream(response.getOutputStream());

//����buffer

                    byte[] buffer=new byte[4*1024]; //4k Buffer

                    int read=0;

//���ļ��ж������ݲ�д������ֽ�����

                    while((read=is.read(buffer))!=-1){

                        baos.write(buffer,0,read);

                    }

//������ֽ���д��response���������

                    os.write(baos.toByteArray());

                }catch(IOException e){

                    e.printStackTrace();

                }finally{

//�ر�����ֽ�����response�����

                    os.close();

                    is.close();

                }

            }

        }

        public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{

//����doGet()����

            doGet(request,response);

        }

    }
