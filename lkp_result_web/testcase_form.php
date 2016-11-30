<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 </head>
<body>
<h3>testcase结果如下：</h3>


<div id="bodyframe" >
 <iframe src="
 <?php
  $vtestcase=  $_GET["testcase"];
  $vtest_config=  $_GET["test_config"];
  $vtbox_group=  $_GET["tbox_group" ];
  $vrootfs=  $_GET["rootfs"];
  $vkconfig=  $_GET["kconfig"];
  $vcompiler=  $_GET["compiler"];
  $vcommit=  $_GET["commit"];
  $vrun=  $_GET["run"];

  
  $url=$vtestcase.'/'.$vtest_config.'/'. $vtbox_group.'/'.$vrootfs.'/'.$vkconfig.'/'.$vcompiler.'/'.$vcommit.'/'.$vrun;
  $url=str_replace("%","%25",$url);
  
  $url="result/".$url;
  echo $url
?>

"
 marginheight="0" marginwidth="0" frameborder="0"  width=100% height=100% id="iframepage" name="iframepage" onLoad="iFrameHeight()" ></iframe>

</div>

</body>
</div>
</html>
