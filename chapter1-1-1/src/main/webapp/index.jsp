<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<body>
<h2>Hello World!</h2>
<a href="/massage/go">我要跳转了！</a>

<div>
    用户名：<input type="text" id="userName" name="userName"/>
    <input type="button" value="查找用户" onclick="search()"/>
</div>
<h2>FORM------GET</h2>
<div>
    <form id="reportGet" action="/massage/report-get" method="get">
        Begin:<input type="text" name="begin" /><br>
        End&nbsp&nbsp :<input type="text" name="end" /><br>
        <input type="submit" value="GET查找报表">
    </form>
</div>
<h2>FORM------POST</h2>
<div>
    <form id="reportPost" action="/massage/report-post" method="post">
        Begin:<input type="text" name="begin" /><br>
        End&nbsp&nbsp :<input type="text" name="end" /><br>
        <input type="submit" value="POST查找报表">
    </form>
</div>
</body>
<script>
    function search() {
        window.open("/massage/detail/data="+document.getElementById("userName").value);
    }
</script>
</html>
