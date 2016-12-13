<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href="table.css" rel="stylesheet"> 
</head>
<body>
<h3>summary结果如下：</h3>


<div id="bodyframe" >  
 <iframe src="
 <?php
  $vcompile=  $_GET["summary"];
  $vcompile=str_replace("%","%25",$vcompile);
  $vcompile="summary/".$vcompile;
  echo $vcompile
?>

"frameborder="0" ></iframe>
<!--" marginheight="0" marginwidth="0" frameborder="0" width=100% height=100% id="iframepage" name="iframepage" onLoad="iFrameHeight()" ></iframe> -->

<h4>表格如下：</h4>
<?php
  $vcompile=  $_GET["summary"];
  $vcompile=str_replace("%","%25",$vcompile);
  $com=substr($vcompile,9);
  system("python getdata.py ".$com);
?>

<div id="restable"></div>

<script src="jquery-1.11.3.min.js"></script>
<script src="jquery.columns.min.js"></script>
<script>
	$.ajax({
                url:'data.json',
                dataType: 'json',
                success: function(json) {
                     restable = $('#restable').columns({
                        data:json
                    });
                }
        });
</script>
</div>
</body>
</html>
