#!/usr/bin/env bash

file_env() {
    local var="$1"
    local fileVar="${var}_FILE"
    local def="${2:-}"
    if [ "${!var:-}" ] && [ "${!fileVar:-}" ]; then
        echo >&2 "error: both $var and $fileVar are set (but are exclusive)"
		exit 1
    fi
    local val="$def"
	if [ "${!var:-}" ]; then
		val="${!var}"
	elif [ "${!fileVar:-}" ]; then
		val="$(< "${!fileVar}")"
	fi
	export "$var"="$val"
	unset "$fileVar"
}


file_env 'ALPHAVANTAG_API_KEY'
file_env 'MONGO_INITDB_ROOT_PASSWORD'

if [ "${1:0:1}" = '-' ]; then
    exec python ./run_app.py "$@"
else
    exec "$@"
fi