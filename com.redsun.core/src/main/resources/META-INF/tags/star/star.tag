<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag body-content="scriptless" display-name="redstar" description="red sun corp"  %>
<span style="color: #0000ff" >v2<jsp:doBody/></span>

<a href="#"onclick="onSpanClick()">onclick
</a>
<%-- 把主体内容保存到bb变量中, 没有指定作用域下, 保存在page --%>
<jsp:doBody var="bb" scope="request" />
<script type="text/javascript">
        function  onSpanClick(){
            alert('bb');
            alert("${bb}");
        }
</script>