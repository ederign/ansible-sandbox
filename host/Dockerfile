# Use Ubuntu as the base image
FROM ubuntu:latest

# Update the package list and install OpenSSH server
RUN apt-get update && apt-get install -y openssh-server

# Create a new user named "user1" with password "password1"
RUN useradd -m user1 && echo "user1:password1" | chpasswd

# Create a new user named "user2" with password "password2"
RUN useradd -m user2 && echo "user2:password2" | chpasswd

# Start the SSH server
RUN service ssh start

# Expose SSH port
EXPOSE 22

# Run the SSH server as the entrypoint
CMD ["/usr/sbin/sshd", "-D"]
