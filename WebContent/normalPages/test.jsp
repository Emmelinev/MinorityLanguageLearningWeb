<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
  <html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <script src="../js/jquery-3.0.0.js" type="text/javascript"></script>
  <script type="text/javascript">
  $(document).ready(function(){
  $("#form01").change( function(){
  var filepath=$("input[name='myFile']").val();
  var extStart=filepath.lastIndexOf(".");
  var ext=filepath.substring(extStart,filepath.length).toUpperCase();
  if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
  alert("ͼƬОԚbmp,png,gif,jpeg,jpgٱʽ");
  return false;
  }else{$("#name01").text(ext)}
  var file_size = 0;
  if ( $.browser.msie) {
  var img=new Image();
  img.src=filepath;
  while(true){
  if(img.fileSize > 0){
  if(img.fileSize>3*1024*1024){
  alert("ͼƬһճԚ100MBc");
  }else{
  var num03 = img.fileSize/1024;
  num04 = num03.toFixed(2)
  $(".size02").text(num04+"KB");
  }
  break;
  }
  }
  } else {
  file_size = this.files[0].size;
  console.log(file_size/1024/1024 + " MB");
  var size = file_size / 1024;
  if(size > 10240){
  alert("ʏԫքτݾճСһŜӬڽ10Má");
  }else{
  var num01 = file_size/1024;
  num02 = num01.toFixed(2)
  $("#size01").text(num02 + " KB");
  }
  }
  return true;
  });
  });
  </script>
  <title>ϞҪ͢τյ</title>
  </head>
  <body>
  <table width="500" cellspacing="0" cellpadding="0">
  <tr>
  <td width="72" id="name01"> </td>
  <td width="242"><input type="file" name="myFile" id="form01" /></td>
  <td width="184" id="size01" class="size02"> </td>
  </tr>
  </table>
  </body>
  </html>