#!/bin/bash

# Internal options, always specified
INTERNAL_OPTS="-Dfile.encoding=UTF-8 -Xss8M -Xmx2G -d64 -noverify -XX:+CMSClassUnloadingEnabled -XX:+UseConcMarkSweepGC -XX:MaxPermSize=512M -XX:ReservedCodeCacheSize=128M"

# Default options, if nothing is specified
DEFAULT_OPTS=""

SBT_VERSION="0.12.4"
SBT_LAUNCHER="$(dirname $0)/project/sbt-launch-$SBT_VERSION.jar"

if [ ! -e "$SBT_LAUNCHER" ];
then
    URL="http://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/$SBT_VERSION/sbt-launch.jar"
    curl -o $SBT_LAUNCHER $URL
fi

# Call with INTERNAL_OPTS followed by SBT_OPTS (or DEFAULT_OPTS). java aways takes the last option when duplicate.
JAVA_BINARY=java
if [ -n "$JAVA_HOME" ]; then
  JAVA_BINARY=$JAVA_HOME/bin/java
fi

exec $JAVA_BINARY ${INTERNAL_OPTS} ${SBT_OPTS:-${DEFAULT_OPTS}} -jar $SBT_LAUNCHER "$@"
