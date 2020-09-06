job("task6_job1"){
  description("my first dsl job")
  scm{
    github('firsttalk26/devops1','master')
  }
  triggers{
    scm("* * * * *")
  }
  steps{
    shell('''sleep 10;
date ;''')
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