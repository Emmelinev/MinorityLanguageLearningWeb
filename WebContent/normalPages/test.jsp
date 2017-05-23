<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
    <script src="../js/bacaling-practice.js" type="text/javascript"></script>
</head>
<body>
   <form action="updateAccount.jsp" method="post"  
            enctype="multipart/form-data">  
            <input type="file" name="pic" id="pic" />  
            <input type="submit" value="上传" />  
        </form> 
        <input type="button" value="send" />
        <script>
        var storage=window.localStorage;
        //写入a字段
     //   storage["a"]=1;
        //写入b字段
      //  storage.a=1;
        //写入c字段
     //   storage.setItem("c",3);
      //  console.log(typeof storage["a"]);
       // console.log(typeof storage["b"]);
      //  console.log(typeof storage["c"]);
        //第一种方法读取
        var a=storage.effect;
        console.log("storage-"+a);
        </script>
</body>
</html>