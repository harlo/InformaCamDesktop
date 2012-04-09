<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <script type="text/javascript" src="js/string.js"></script>
    <script type="text/javascript" src="js/vars.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/json2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/org/cometd.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.cometd.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
    <%--
    The reason to use a JSP is that it is very easy to obtain server-side configuration
    information (such as the contextPath) and pass it to the JavaScript environment on the client.
    --%>
    <script type="text/javascript">
        var config = {
            contextPath: '${pageContext.request.contextPath}'
        };
    </script>
    <script type="text/javascript" src="js/handlers.js"></script>
	<script type="text/javascript" src="js/jquery.tmpl.js"></script>
	<script type="text/javascript" src="js/sammy.js"></script>
	<script type="text/javascript" src="js/ui.js"></script>
	<script type="text/javascript" src="js/media.js"></script>
	<link rel="stylesheet" type="text/css" href="css/ic.css" />
    <title>InformaCam - powered by The Guardian Project</title>
</head>
<body>
	<div id="alert_holder">
		<h1 id="alert_title"></h1>
		<div id="alert_text"></div>
		<a id="alert_button" class="dismissal" onclick="removeAlert();">OK</a>
	</div>
	<div id="ic_header">
		<div id="ic_logo">
			<table>
				<tr>
					<td><img src="images/ic_logo.png" /></td>
					<td>
						<h1>InformaCam</h1>
						<p>Powered by<br /><a href="https://guardianproject.info" target="_blank">The Guardian Project</a></p>
					</td>
				</tr>
			</table>
		</div>
		
		<ul id="ic_nav">
			<li><a href="#media/">Media</a></li>
			<li><a href="#submissions/">Submissions</a></li>
			<li><a href="#admin/">Admin</a></li>
			<li><a href="#help/">Help</a></li>
		</ul>
	</div>
	
	<div id="ic_main">
		<div id="ui_media">
			<table class="ic_table">
				<tr>
					<td id="media_holder" width="75%">

						<div id="media_options">
							<ul class="ic_menu_button">
								<li>
									<a onclick="loadMedia();">Load...</a>
								</li>
								<li>
									<a>Views</a>
									<div class="ic_dropdown">
										<ul>
											<li>Normal View</li>
											<li>Map View</li>
											<li>Motion View</li>
											<li>Network View</li>
										</ul>
									</div>
								</li>
								<li>
									<a>Options</a>
									<div class="ic_dropdown">
										<ul>
											<li>Share Video</li>
											<li>Export Metadata As...</li>
											<li>View Submission Info</li>
										</ul>
									</div>
								</li>
								<li class="ic_menu_buttonOverride">
									Show Media As: 
									<div class="ic_toggle" id="mediaView">
										<a onclick="toggleValue(this);media.setCurrentDisplay(Display.REDACTED);" rel="redacted" class="selected">Redacted</a><a onclick="toggleValue(this);media.setCurrentDisplay(Display.UNREDACTED);" rel="unredacted">Unredacted</a>
									</div>
								</li>
							</ul>
						</div>
						<div id="media_frame">
							
						</div>
					</td>
					<td id="metadata_holder">
						<h2 id="media_title">media title would go here</h2>
						<div id="metadata_readout">
							<ul>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
								<li>a list of things</li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="ui_submissions">
			<h1>submissions main</h1>
		</div>
		
		<div id="ui_admin">
			<h1>admin main</h1>
		</div>
		
		<div id="ui_help">
			<h1>help main</h1>
		</div>
		
	</div>
	
	<div id="ic_footer">
		<div id="ic_login"></div>
	</div>
</body>
</html>
