job("task6_job1"){
  description("my first dsl job")
  scm{
    github('firsttalk26/devops1','master')
  }
  triggers{
    scm("* * * * *")
  }
  steps{
    shell('''if ls /root/.jenkins/workspace/ | grep task6_data
then
  cp -rf * /root/.jenkins/workspace/task6_data/
  echo "folder created"
else
  sudo mkdir /root/.jenkins/workspace/task6_data
  cp -rf * /root/.jenkins/workspace/task6_data/
fi''')
    
    }
}
job("task6_job2"){
  description("my 2nd dsl job")
  triggers{
    upstream("task6_job1",'SUCCESS')
  }
  steps{
    shell('''if ls /root/.jenkins/workspace/task3_data/ | grep .html
then
  if kubectl get deployment | grep webserver-deployment 
  then
    echo "already webserver-deployment created"
  else
    kubectl create -f /root/.jenkins/workspace/task3/webserver_deployment.yml
  fi
else
  echo "it's not html code"
  if ls /tas3/ | grep .phpcode
  then
    echo " it is php code"
    if kubectl get deployment | grep webapp-deployment
    then
      echo " webapp-deployment is already created"
    else
      kubectl create -f /root/.jenkins/workspace/task3/php.yml
    fi
  else
    echo "recheck code"
  fi

fi''')
    }
}
job("task6_job3"){
  description("my first dsl job")
  triggers{
    upstream("task6_job2",'FAILURE')
  }
  steps{
    shell('''podname=$(kubectl get pods -l type=html --output=jsonpath={.items..metadata.name})

kubectl cp /root/.jenkins/workspace/task3_data/  $podname:/usr/local/apache2/htdocs/ 

status=$(curl -o /dev/null -s -w "%{http_code}" http://192.168.99.109:30001)
if [ $status == 200 ]
then
  exit 0
else
  exit 1
  
fi
''')
  
    }
  publishers{
  mailer("firsttalk26@gmail.com", false, false)
  }
}