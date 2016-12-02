<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 </head>
<body>
<h3>summary表格如下：</h3>


<div id="bodyframe" >
 <iframe src="
 <?php
  $vcommit=  $_GET["commit"];
  $vcommit=str_replace("%","%25",$vcommit);
  $vtbox_group=  $_GET["tbox_group"];
  $vtbox_group=str_replace("%","%25",$vtbox_group);
  $vtable="html_tables/".$vcommit."_".$vtbox_group.".html";
  echo $vtable
?>

"
 marginheight="0" marginwidth="0" frameborder="0" width=100% height=100% id="iframepage" name="iframepage" onLoad="iFrameHeight()" ></iframe>

</div>

</body>
</div>
</html>
