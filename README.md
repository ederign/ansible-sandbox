###

https://docs.ansible.com/ansible/latest/getting_started/index.html#

cat hosts.bkp
[myvirtualmachines]
my_host ansible_host=localhost ansible_user=user1 ansible_port=2222

### ansible host

docker run -d -p 2222:22 --name awx_1 ansible-host

### use inventory

ansible virtualmachines -m ping -i inventory.yaml

### run playbook:

ansible-playbook -i inventory.yaml playbook.yaml
