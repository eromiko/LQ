<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>员工信息</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/style.css" />
<link rel="shortcut icon" href="../assets/ico/favicon.ico">
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="./Person.js"></script>
</head>
<body>
<%String paramVal =request.getParameter("personId");%>
<input type="hidden" id="personId" name="personId" value=<%=paramVal%> />
<div class="container">
	<div class="table-responsive">
		<table id="personInfo" class="table table-bordered">
			<tbody id="basData">
				<tr class="table-list">
					<td>姓名</td>
					<td id="name" class="text-primary"></td>
					<td>性别</td>
					<td id="gender" class="text-primary"></td>
					<td>出生年月<br>(岁)
					</td>
					<td id="birthday" class="text-primary"></td>
				</tr>
				<tr class="table-list">
					<td>民族</td>
					<td id="nation" class="text-primary"></td>
					<td>籍贯</td>
					<td id="nativeBirth" class="text-primary"></td>
					<td>出生地</td>
					<td id="birthAddress" class="text-primary"></td>
				</tr>
				<tr class="table-list">
					<td>入党时间</td>
					<td id="rdTime" class="text-primary"></td>
					<td>参加工作时间:</td>
					<td id="startWorkTime" class="text-primary"></td>
					<td>健康状态</td>
					<td id="healthy" class="text-primary"></td>
				</tr>
				<tr class="table-list">
					<td>专业技术职务</td>
					<td id="speTechPos" colspan="3" class="text-primary"></td>
					<td>熟悉专业<br>有何专长
					</td>
					<td id="specialty" class="text-primary"></td>
				</tr>
				<tr class="table-list">
					<td rowspan="2">学 历<br>学 位
					</td>
					<td>全日制<br>教 育
					</td>
					<td id="fulltimeEdu" colspan="2" class="text-primary"></td>
					<td>毕业院校<br>系及专业
					</td>
					<td id="fulltimeSpe" colspan="2" class="text-primary"></td>
				</tr>
				<tr class="table-list">
					<td>在职<br>教 育
					</td>
					<td id="jobEdu" colspan="2" class="text-primary"></td>
					<td>毕业院校<br>系及专业
					</td>
					<td id="jobSpe" colspan="2" class="text-primary"></td>
				</tr>
				<tr class="table-list">
					<td colspan="2">现任职务</td>
					<td id="currPost" colspan="4" style="text-align: left" class="text-primary"></td>
				</tr>
				<tr class="table-list">
					<td>简历</td>
					<td id="jl" colspan="5" style="text-align: left" class="text-primary"></td>
				</tr>
				<tr class="table-list">
					<td>奖惩情况</td>
					<td id="jcqk" colspan="5" style="text-align: left" class="text-primary"></td>
				</tr>
				<tr class="table-list">
					<td>年度考核结果</td>
					<td id="khqk" colspan="5" style="text-align: left" class="text-primary"></td>
				</tr>
				<tr class="table-list" id="familyMembersHead"> 
				</tr>
			</tbody>
		</table>
		</div>
	</div>
</body>
</html>