<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/jqueryApi/jqueryui/themes/ui-lightness/jquery-ui.css"/>
<script type="text/javascript" src="${ctx}/static/jquery/jqueryApi/jqueryui/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jqueryApi/jqueryui/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jqueryApi/jqueryui/ui/jquery.ui.position.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jqueryApi/jqueryui/ui/jquery.ui.menu.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jqueryApi/jqueryui/ui/jquery.ui.autocomplete.js"></script>
<style>
.ui-autocomplete {
	max-height: 150px;
	overflow-y: auto; /* prevent horizontal scrollbar */
	overflow-x: hidden;
	border:1px solid #94BAE7;
}
/* IE 6 doesn't support max-height   
* we use height instead, but this forces the menu to always be this tall   
*/
* html .ui-autocomplete {
	height: 150px;
}
</style>