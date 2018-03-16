# Fault-tolerant Distributed Stack with RAFT Consensus
Implemenation of faul tolerant and distributed stack with using RAFT consensus protocol using Atomix

## Compiling code
- Running `sh run_leader.sh` compiles, packages and starts the initial node of cluster
- Running `sh run_followers.sh` starts 4 additional nodes to the cluster

## Running Program
- Run `mvn exec:java@client` will start the client and show you the prompt asking for user inputs.

## What Works
- All operations as supported by a normal stack
- Handles the cases when an invalid operation is tried
