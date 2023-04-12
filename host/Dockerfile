# Use Ubuntu as the base image
FROM ubuntu:latest

ARG ssh_pub_key

# Update the package list and install OpenSSH server
RUN apt-get update && apt-get install -y openssh-server

# Create a new user named "user1" with password "password1"
RUN useradd -m user1 && echo "user1:password1" | chpasswd

RUN mkdir -p /home/user1/.ssh && \
    touch /home/user1/.ssh/authorized_keys && \
    chmod 700 /home/user1/.ssh && \
    chmod 600 /home/user1/.ssh/authorized_keys && \
    echo "$ssh_pub_key" >> /home/user1/.ssh/authorized_keys && \
    chown -R user1:user1 /home/user1/.ssh

# Create a new user named "user2" with password "password2"
RUN useradd -m user2 && echo "user2:password2" | chpasswd

# Start the SSH server
RUN service ssh start

# Expose SSH port
EXPOSE 22

# Run the SSH server as the entrypoint
CMD ["/usr/sbin/sshd", "-D"]
