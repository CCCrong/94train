<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 17-9-7
  Time: 下午2:46
  To change this template use File | Settings | File Templates.
--%>

<jsp:include page="common/IncludeTop.jsp"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="row">
        <div class="col-xs-4" ></div>
        <div class="col-xs-4" >

            <%
                String msg = (String) request.getAttribute("Msg");
                if(msg!=null)
                {
            %>
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span class="sr-only">Error:</span>
                ${Msg}
            </div>
            <%
                }
            %>

            <form class="form-group" action="\registerAndLogin\Login.do" method="post">
                <h2>欢迎登录</h2>
                <label for="PhoneNum">用户名/手机号</label>
                <input type="number" id="PhoneNum" name="PhoneNum"class="form-control" placeholder="用户名/手机号" required autofocus>
                <label for="Password">密码</label>
                <input type="password" id="Password" name="Password" class="form-control" placeholder="密码" required>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> 记住密码 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">忘记密码</a>
                    </label>
                </div>
                <input class="btn btn-lg btn-primary btn-block" type="submit">登录</input>
            </form>
        </div>
    </div> <!-- /container -->
</div>

<jsp:include page="common/IncludeBottom.jsp"/>
