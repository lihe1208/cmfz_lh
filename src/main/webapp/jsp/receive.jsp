<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<script src="../js/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
<script type="text/javascript">
    var goEasy = new GoEasy({
        appkey: 'BC-c07f58e282ac45f493d42641b6a5259e'
    });

    goEasy.subscribe({
        channel: 'lh',
        onMessage: function (message) {
            alert('收到：' + message.content);
        }
    });
</script>



