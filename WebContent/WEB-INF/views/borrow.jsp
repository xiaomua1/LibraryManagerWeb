<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>购票信息列表</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	function timestampToTime(timestamp) {
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        var m = date.getMinutes() + ':';
        var s = date.getSeconds();
        if(s < 10){
        	s = '0' + s ;
        }
        return Y+M+D+h+m+s;
    }
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'购票信息列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	        url:"BorrowServlet?method=BorrowList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'user',title:'用户',width:100,
 		        	formatter: function(value,row,index){
 		        		if(value != null && value != 'undefined')
 		        			return value.username;
 		        	}	
 		        },
 		        {field:'book',title:'公交线路名称',width:200,
 		        	formatter: function(value,row,index){
 		        		if(value != null && value != 'undefined')
 		        			return value.name;
 		        	}	
 		        },
 		      	{field:'status',title:'状态',width:70, 
		        	formatter: function(value,row,index){
		        		switch(value){
		        			case 1:{
		        				return '已购票 ';
		        			}
		        			case 2:{
		        				return '已退票';
		        			}
		        			case 0:{
		        				return '未知状态';
		        			}
		        		}
					}
				},
				{field:'number',title:'数量',width:50},
 		        {field:'borrowTime',title:'购票时间',width:150, 
 		        	formatter: function(value,row,index){
 		        		return timestampToTime(value);
 					}
				},
 		        {field:'returnTime',title:'退票时间',width:150, 
					formatter: function(value,row,index){
						if(value != '' && value != null && value != 'undefined')
							return timestampToTime(value);
 					}	
 		       	},
	 		]], 
	        toolbar: "#toolbar"
	    }); 
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    }); 
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    
	  //归还
	    $("#return-btn").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
            	var selectRow = $("#dataList").datagrid("getSelected");
            	if(selectRow.status != 1){
            		$.messager.alert("消息提醒", "该票状态不可退票!", "warning");
            		return;
            	}
            	$("#returnDialog").dialog("open");
            }
	    });
	    
	  	
	  	
	  	
	  //设置退票窗口
	    $("#returnDialog").dialog({
	    	title: "退票登记信息",
	    	width: 500,
	    	height: 250,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'提交',
					plain: true,
					iconCls:'icon-edit',
					handler:function(){
						var validate = $("#returnForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "BorrowServlet?method=BookReturn&t="+new Date().getTime(),
								data: $("#returnForm").serialize(),
								dataType:'json',
								success: function(data){
									if(data.type == "success"){
										$.messager.alert("消息提醒","退票成功!","info");
										//关闭窗口
										$("#returnDialog").dialog("close");
										//刷新表格
										$("#dataList").datagrid("reload");
										$("#dataList").datagrid("uncheckAll");
									} else{
										$.messager.alert("消息提醒",data.msg,"warning");
										return;
									}
								}
							});
						}
					}
				},
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#borrow_name").textbox('setValue', selectRow.book.name);
				$("#return-id").val(selectRow.id);
				$("#borrow_number").numberbox('setValue', selectRow.number);
				$("#real_return_number").numberbox({'max':selectRow.number});
			}
	    });
	  	
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			username:$("#search-username").textbox('getValue'),
	  			bookName:$("#search-book-name").textbox('getValue'),
	  			status:$("#search-status").combobox('getValue')
	  		});
	  	});
	  
	  	
	  	
	});

	</script>
</head>
<body>
	<!-- 购票信息列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> 
	    
	</table> 
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="return-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">退票</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="margin-left: 10px;">
			用户名称：<input id="search-username" class="easyui-textbox" />
			公交线路名称：<input id="search-book-name" class="easyui-textbox" />
			购票状态：<select class="easyui-combobox" style="width:100px;" id="search-status">
				<option value="-1">全部状态</option>
				<option value="1">已购票</option>
				<option value="2">已退票</option>
			</select>
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>
	
	</div>
	
	
	<!-- 退票窗口 -->
	<div id="returnDialog" style="padding: 10px">
    	<form id="returnForm" method="post">
	    	<input type="hidden" name="id" id="return-id" >
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>公交线路名称:</td>
	    			<td><input id="borrow_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" readonly="readonly" /></td>
	    		</tr>
	    		<tr>
	    			<td>已购票数量:</td>
	    			<td><input id="borrow_number" style="width: 200px; height: 30px;" class="easyui-numberbox" type="text" readonly="readonly" /></td>
	    		</tr>
	    		<tr>
	    			<td>退票数量:</td>
	    			<td><input id="real_return_number" name="realReturnNumber" style="width: 200px; height: 30px;" class="easyui-numberbox" type="text" data-options="min:1,precision:0,required:true, missingMessage:'请填写退票数量'" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
</body>
</html>