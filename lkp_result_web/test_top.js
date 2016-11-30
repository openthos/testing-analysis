/**
 * Created with PyCharm.
 * User: jennyzhang
 * Date: 16-9-19
 * Time: 下午12:51
 * To change this template use File | Settings | File Templates.
 */
$(document).ready(function(){
        //初始加载第一个选择框
        var source   = $("#entry-template").html();
        var template = Handlebars.compile(source);