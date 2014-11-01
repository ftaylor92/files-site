svn co --username=ftaylor92 --password=matt8888 https://svn-fmtmac.forge.cloudbees.com/files-site/

URLs:
http://files-site.fmtmac.cloudbees.net/xslt.jsp
http://files-site.fmtmac.cloudbees.net/image-rotate.jsp

for fmtmac2 cloudbees account:
http://files-site.fmtmac2.cloudbees.net
mvn clean compile package
goto: cloudbees site, then hit Apps, files-site, upload WAR file, select target/files-site.war

