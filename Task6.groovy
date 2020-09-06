job("task6_job1"){
  description("my first dsl job")
  scm{
    github('firsttalk26/devops1','master')
  }
  triggers{
    scm("* * * * *")
  }
  steps{
    shell('''if ls /root/.jenkins/workspace/ | grep task3_data
then
  cp -rf * /root/.jenkins/workspace/task3_data/
  echo "folder created"
else
  sudo mkdir /root/.jenkins/workspace/task3_data
  cp -rf * /root/.jenkins/workspace/task3_data/
fi''')
    shell('date')
    }
}
job("task6_job2"){
  description("my first dsl job")
  triggers{
    upstream("task6_job1",'SUCCESS')
  }
  steps{
    shell('''sleep 10;
date ;''')
    shell('date')
    }
}
job("task6_job3"){
  description("my first dsl job")
  triggers{
    upstream("task6_job2",'FAILURE')
  }
  steps{
    shell('''sleep 10;
date ;''')
    shell('date')
    }
  publishers{
  mailer("firsttalk26@gmail.com", false, false)
  }
}