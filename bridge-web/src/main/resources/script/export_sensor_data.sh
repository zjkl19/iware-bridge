#!/bin/bash

# 脚本路径
SELF_SHELL_PATH=$(cd `dirname $0`; pwd)
SELF_SHELL_NAME=`basename $0`
SELF_LOG_PATH=/var/log/cii/sh/download_sensor_data.log

if [  ! -f $SELF_LOG_PATH ];then
   mkdir -pv /var/log/cii/sh/
   touch $SELF_LOG_PATH
fi

install_log()
{
    DATE_TIME=`date +'%Y-%m-%d %H:%M:%S.%N'`
    DATE_TIME23="${DATE_TIME:0:23}"
     printf "%-s%-s%-s\n" "[${DATE_TIME23}]" "[${SELF_SHELL_NAME}]" "$*" | tee -a $SELF_LOG_PATH
}


if [ $# -ne 7 ];then
	[ ! -f SELF_LOG_PATH ] && install_log "请输入正确的参数!" && return 1
fi

coding1=$1 #第一个传感器编号
type1=$2 #传感器类型 1:基康 2:其他 3:称重
coding2=$3 #第二个传感器编号
type2=$4 #传感器类型 1:基康 2:其他 3:称重
starttime=$5 #开始时间
endtime=$6 #结束时间
filepath=$7 #文件绝对路径

downloadCVS()
{

	starttime=`date -d @$starttime "+%Y-%m-%d %H:%M:%S"`
	endtime=`date -d @$endtime "+%Y-%m-%d %H:%M:%S"`
	str=""
	jkstr="SELECT collecttime AS sampling_time, paravalue AS value, paraid as sensor_coding FROM composedata WHERE collecttime >= '$starttime' AND collecttime <= '$endtime' AND paraid = '$coding1'"
	dhstr="SELECT sampling_time, value, sensor_coding FROM tb_sensor_converge WHERE sampling_time >= '$starttime' AND sampling_time <= '$endtime' AND sensor_coding = '$coding1'"
	wstr="SELECT tsw.sampling_time, tsw.total_weight AS value, tsw.sensor_coding, tsw.license_plate,tsw.single_shaft_nuber,atmr.name AS model_name FROM tb_sensor_weight tsw LEFT JOIN tb_axle_type_model_rel atmr ON atmr.model_id = tsw.models WHERE sampling_time >= '$starttime' AND sampling_time <= '$endtime' AND sensor_coding = '$coding1'"

	#仅查询单个传感器
	if [[ $coding2 = '-1' ]] && [[ $type2 = '-1' ]];then
		if [[ $type1 = '1' ]]; then
			str="$jkstr ORDER BY paraid ASC, collecttime ASC"
			str="SELECT tr.sensor_coding AS ""传感器编码"", sampling_time AS ""采样时间"", value AS ""测值"" FROM($str) AS tr"
		fi
		if [[ $type1 = '2' ]]; then
			str="$dhstr  ORDER BY sensor_coding ASC, sampling_time ASC"
			str="SELECT tr.sensor_coding AS ""传感器编码"", sampling_time AS ""采样时间"", value AS ""测值"" FROM($str) AS tr"
		fi
		if [[ $type1 = '3' ]]; then
			str="$wstr ORDER BY sensor_coding ASC, sampling_time ASC"
			str="SELECT tr.sensor_coding AS ""传感器编码"", sampling_time AS ""采样时间"", value AS ""总重"", single_shaft_nuber AS ""轴数"", model_name AS ""车型"", license_plate AS ""车牌号"" FROM($str) AS tr"
		fi
	fi

	if [ $type1 = $type2 ];then
		if [ $type1 = '1' ]; then
			str="$jkstr OR paraid = '$coding2' ORDER BY paraid ASC, collecttime ASC"
			str="SELECT tr.sensor_coding AS ""传感器编码"", sampling_time AS ""采样时间"", value AS ""测值"" FROM($str) AS tr"
		fi
		if [ $type1 = '2' ]; then
			str="$dhstr OR sensor_coding = '$coding2' ORDER BY sensor_coding ASC, sampling_time ASC"
			str="SELECT tr.sensor_coding AS ""传感器编码"", sampling_time AS ""采样时间"", value AS ""测值"" FROM($str) AS tr"
		fi
		if [ $type1 = '3' ]; then
			str="$wstr OR sensor_coding = '$coding2' ORDER BY sensor_coding ASC, sampling_time ASC"
			str="SELECT tr.sensor_coding AS ""传感器编码"", sampling_time AS ""采样时间"", value AS ""总重"", single_shaft_nuber AS ""轴数"", model_name AS ""车型"", license_plate AS ""车牌号"" FROM($str) AS tr"
		fi
	fi

#	if [ $type1 != $type2 ];then
#		if [ $type1 = '1' -a $type2 = '2' ] || [ $type1 = '2' -a $type2 = '1' ];then
#			str="SELECT rs.* FROM($jkstr UNION ALL $dhstr) AS rs ORDER BY sensor_coding ASC, sampling_time ASC"
#		fi
#		if [ $type1 = '1' -a $type2 = '3' ] || [ $type1 = '3' -a $type2 = '1' ];then
#			str="SELECT rs.* FROM($jkstr UNION ALL $wstr) AS rs ORDER BY sensor_coding ASC, sampling_time ASC"
#		fi
#		if [ $type1 = '2' -a $type2 = '3' ] || [ $type1 = '3' -a $type2 = '2' ];then
#			str="SELECT rs.* FROM($dhstr UNION ALL $wstr) AS rs ORDER BY sensor_coding ASC, sampling_time ASC"
#		fi
#	fi

#	str="SELECT tr.sensor_coding AS ""传感器编码"", sampling_time AS ""采样时间"", value AS ""测值"" FROM($str) AS tr"

su - postgres <<CWY
psql -U postgres -d bridge -w -c "\COPY ($str) TO '$filepath' WITH csv header encoding 'GBK'"
exit
CWY
	if [ $? -ne 0 ];then
		install_log "数据导出失败" && return 1
	else
		install_log "数据导出成功,路径-----> $filepath" && return 0
	fi
}

downloadCVS
if [ $? -ne 0 ];then
   echo "result:"-1
else
   echo "result:"1
fi



