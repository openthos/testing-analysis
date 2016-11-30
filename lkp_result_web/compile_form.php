<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 </head>
<body>
<h3>compile结果如下：</h3>


<div id="bodyframe" >  
 <iframe src="
 <?php
  $vcompile=  $_GET["compile"];
  $vcompile=str_replace("%","%25",$vcompile);
  $vcompile="compile/".$vcompile;
  echo $vcompile
?>

"
 marginheight="0" marginwidth="0" frameborder="0"  width=100% height=100% id="iframepage" name="iframepage" onLoad="iFrameHeight()" ></iframe>

</div>

</body>
</div>
</html>
