from ftplib import FTP
import os

ftp = FTP('itrustgreen.ze.am')
ftp.set_pasv(False);
ftp.login(os.environ['AWS_DEPLOYER_USERNAME'], os.environ['AWS_DEPLOYER_PASSWORD'])
print ('login success')
os.chdir('target')
ftp.cwd('/opt/tomcat9/webapps')
print (ftp.pwd())

print ('found path')
print ('Sending...')
f = open('iTrust.war', 'rb')

ftp.storbinary('STOR iTrust.war', f)

f.close()
ftp.quit()
print ('Done.')

