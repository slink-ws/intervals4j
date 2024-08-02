#!/usr/bin/env bash

if [ -z "$1" ]; then
  echo "error: version not set"
  exit 1
fi

DIR="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

cd ${DIR}
mvn versions:set -DnewVersion=$1
