package com.iware.common.pojo;

import java.util.List;
import java.util.stream.Collectors;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CustomPageInfo<T> extends PageInfo<T> {

    private static final long serialVersionUID = -3331329672102428229L;

    /**
     * @param list     为最终返回的数据列表
     * @param pageList 为分页的数据列表
     */
    public CustomPageInfo(List list, List pageList) {
        super(pageList);
        if(list == null){
        	super.setList(Lists.newArrayListWithExpectedSize(0));
        }else{
        	super.setList(list);
        }
    }

    public CustomPageInfo(List<T> list, Integer pageSize, Integer pageNum, long total) {
        super(list);
        super.setPageNum(pageNum);
        super.setPageSize(pageSize);
        super.setTotal(total);
        Integer pages = (int) ((total % pageSize) == 0 ? (total / pageSize) : (total / pageSize) + 1);
        super.setPages(pages);
    }

    public CustomPageInfo(List<T> list, Integer pageSize, Integer pageNum) {
        super(list);

        List<T> resultList = list.stream()
                                 .skip((pageNum - 1) * (long)pageSize)
                                 .limit(pageSize)
                                 .collect(Collectors.toList());

        super.setPageNum(pageNum);
        super.setPageSize(pageSize);
        super.setTotal(list.size());
        Integer pages = (list.size() % pageSize) == 0 ? (list.size() / pageSize) : (list.size() / pageSize) + 1;
        super.setPages(pages);
        super.setList(resultList);
    }
}
