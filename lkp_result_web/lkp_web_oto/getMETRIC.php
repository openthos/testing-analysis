<!DOCTYPE html>

<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<title>Highcharts</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="stylesheet" type="text/css" href="./highcharts_files/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="./highcharts_files/style.css">
<script src="./highcharts_files/hm.js"></script><script src="./highcharts_files/share.js"></script><link rel="stylesheet" href="./highcharts_files/share_style0_24.css"></head>


<script language="JavaScript">



<?php
  $benchmarks=$_GET["benchmarks"];
 $teststr= 'cut -d , -f 3 ./csv/'.$benchmarks.'.csv | grep -v "config2"   | sort  | uniq | ./gethostname.py';
 echo "//".$teststr."\n";
 echo " var hostname_dict = ";
 $test=passthru($teststr);
 echo ";";
?>



function all_init()                                                                                                                                  
{
 hostname_changeselect("init");
}


function hostname_changeselect(aid)
{



 if(aid=="init")
  {
      for(ahostname in hostname_dict){

        var text = "<option>"+ahostname+"</option>";
                $("#hostname").append(text);
        console.log(text);
      }   
  }                                                                                                                                                  


}

</script>



<body onload="javascript:all_init();">

	<div id="demo" >
		<div id="demo_content">

			<div class="page-header">
				<h1>LKP result charts</h1>
				<div class="subtitle">
				<p class="single"><a href="https://01.org/zh/lkp" target="_blank">Linux Kernel Performance</a> test case result</p>
				</div>
				<div class="clear"></div>

				</div>

				<div align="left">

<!--
				<iframe src="cache/out.html"
				width="100%" height="500"
				frameborder="no">
				<a href="cache/out.html">CHART</a>
				</iframe>
				<div class="clearfix"></div>
-->

			<?php

			#$xaxis=$_GET["xaxis"];
			$benchmarks=$_GET["benchmarks"];
			#echo var_dump($xaxis).'<br>';
			#echo var_dump($benchmarks).'<br>';
			#echo '<h3><a href="chart.php?xaxis='.$xaxis.'&benchmarks='.$benchmarks.'&count=7,8,9">'.$benchmarks.'</a></h3>';
           # echo '<h3>'.$benchmarks.'</h3>';
			#if(($xaxis == "benchmark") && ($benchmarks == "all")){

			#$shell_result0 = shell_exec("./benchmarks_count.sh");
			#echo $shell_result;
            
			#echo '
			#	<iframe src="cache/count.html"
			#	width="100%" height="2500"
			#	frameborder="no">
			#	<a href="cache/count.html">CHART</a>
			#	</iframe>
			#	<div class="clearfix"></div>
			#';

			#}else{

			$csv_path = "csv/".$benchmarks.".csv";
                        //echo  $csv_path;
			$handle = fopen($csv_path, "r");
			$buffer = fgets($handle);
			fclose($handle);

			$head_data=explode(",",$buffer);
/*
			$t_exec = 'python genhtml.py -i '.$csv_path.' -o cache/out.html -m '.$head_data[10].' -b '.$benchmarks.' -c gcc-4.9 -x '.$xaxis ;
			#echo $t_exec;
			$shell_result = shell_exec("$t_exec");
			#echo $shell_result;
*/

                        echo ' <form action="chart.php?" method="post" >';
                        echo ' <input type="text" name="benchmarks" value="'.$benchmarks.'" readonly="true"><br>';

                        echo '  <select id="hostname" name="hostname" > ';
                         echo      '</select name="hostname" >';


                        echo '<input type="submit" value="SUBMIT">';
                        echo '     ';
                        echo '<input type="reset" value="CLEAR" >';
			echo '<ul type="disc">';
			$head_count=7;
                        $head_data=array_slice($head_data,7);
			foreach ($head_data as $value){
				#echo '<li><a href="chart.php?xaxis='.$xaxis.'&benchmarks='.$benchmarks.'&count='.$head_count.'">'.$value.'</a></li>';
                                echo '<li><input type="checkbox" name="count[]" value='.$head_count.'>'.$value.'</li>';
				#echo '<li><a href="chart.php?&benchmarks='.$benchmarks.'&count='.$head_count.'">'.$value.'</a></li>';
                $head_count++;
			}
			echo '</ul>';
			//echo var_dump($head_data).'<br>';
			#}
                         
                        echo '</form>'

			?>

			</div>
			<div class="clear"></div>


			</div>
		<div class="clear"></div>


	</div>



<script type="text/javascript" src="./highcharts_files/jquery.min.js"></script>
<script type="text/javascript" src="./highcharts_files/bootstrap.min.js"></script>
<script type="text/javascript" src="./highcharts_files/jquery.scrollUp.min.js"></script>
<script type="text/javascript" src="./highcharts_files/common.js"></script>	<script type="text/javascript" src="./highcharts_files/highcharts.js"></script>
	<script type="text/javascript" src="./highcharts_files/exporting.js"></script>
				<script type="text/javascript" src="./highcharts_files/line-basic.js"></script>

</body></html>
