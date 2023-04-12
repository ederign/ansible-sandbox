#bin/sh
docker build -t ansible-host --build-arg ssh_pub_key="$(cat ~/.ssh/id_rsa.pub)" .