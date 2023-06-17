version:1.3.3
当前文件夹下的sql脚本为初始化数据库所用
bridge_ddl.sql：数据库建表脚本，含普通表转时序表
bridge_dml.sql：数据库数据初始化脚本
bridge.backup：数据库一键导入脚本，不用分步执行上述2个脚本文件
    
#导入数据库
pg_restore -h 127.0.0.1 -p 5432 -U postgres -w -d bridge -v "$dir/bridge.backup"
