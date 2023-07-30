FROM ubuntu:18.04

ARG AGENT_URL=https://usw1.dm1-us.informaticacloud.com/saas/download/installer/linux64/agent64_install_ng_ext.bin

# Updates Agent Core Ini File <Secure Agent installation directory>/apps/agentcore/conf/infaagent.ini
# Agent Core InfaAgent.GroupName=<Secure Agent group name>
ARG AGENT_GROUP_NAME=NA

# install system tools
RUN apt-get update && apt-get install -y \
curl \
less \
locales \
locales-all \
sudo \
unzip

# Set the locale, Locale defaults are necessary for agent to operate correctly
RUN sed -i -e 's/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/' /etc/locale.gen && \
    locale-gen
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

# We need to run docker image under a different user than root 
# Secure agent process engine can't be run under root account
RUN useradd --create-home -ms /bin/bash -U iics

USER iics

# 1. Download and prepare Installer
# 2. Set file permissions
# 3. Install using silent install and the default location
# 4. Cleanup
# 5. Set Agent Group
# 6. pre-create directories accessible as Host Volues to ensure that they are owned by correct user and allow access from Host System
RUN curl -o /tmp/agent64_install.bin $AGENT_URL && \
    chmod +x /tmp/agent64_install.bin && \ 
    /tmp/agent64_install.bin -i silent && \
    rm -rf /tmp/agent64_install.bin && \
    echo "\nInfaAgent.GroupName=$AGENT_GROUP_NAME" >> /home/iics/infaagent/apps/agentcore/conf/infaagent.ini && \
    cat /home/iics/infaagent/apps/agentcore/conf/infaagent.ini && \
    mkdir -p /home/iics/infaagent/apps/process-engine/logs && \
    mkdir -p /home/iics/infaagent/apps/process-engine/data && \
    mkdir -p /home/iics/infaagent/apps/Data_Integration_Server/data && \
    mkdir -p /home/iics/infaagent/apps/Data_Integration_Server/logs && \
    mkdir -p /home/iics/infaagent/apps/process-engine/ext && \
    mkdir -p /home/iics/infaagent/apps/Data_Integration_Server/ext


WORKDIR /home/iics/infaagent/apps/agentcore


## Define Volumes for Shared Data Staging area For Logs and local storage for apps and make them mappable to Host file System
VOLUME [ "/data", "/home/iics/infaagent/apps/process-engine/logs", "/home/iics/infaagent/apps/process-engine/data", "/home/iics/infaagent/apps/Data_Integration_Server/data", "/home/iics/infaagent/apps/Data_Integration_Server/logs", "/home/iics/infaagent/apps/process-engine/ext", "/home/iics/infaagent/apps/Data_Integration_Server/ext" ]


## Ports used by the agent that might be used for external Connections
# 7080 Process Engine Shutdown Port
# 7443 Process Engine https port
# 5432 Process Engine Postgres DB
EXPOSE 7080 7443 5432 

RUN ls -ltr

## this script ensures start of the agent and graceful shutdown by providing proper termination handler to stop main aget core process forked during start
COPY run_agent.sh .

# start cmd
CMD [ "./run_agent.sh" ]