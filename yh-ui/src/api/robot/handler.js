import request from '@/utils/request'

// 查询处理器列表
export function listHandler(query) {
  return request({
    url: '/robot/handler/list',
    method: 'get',
    params: query
  })
}
export function listAll(data) {
  return request({
    url: '/robot/handler/listAll',
    method: 'post',
    data: data
  })
}

// 查询处理器详细
export function getHandler(id) {
  return request({
    url: '/robot/handler/' + id,
    method: 'get'
  })
}

// 新增处理器
export function addHandler(data) {
  return request({
    url: '/robot/handler',
    method: 'post',
    data: data
  })
}

// 修改处理器
export function updateHandler(data) {
  return request({
    url: '/robot/handler',
    method: 'put',
    data: data
  })
}

// 删除处理器
export function delHandler(id) {
  return request({
    url: '/robot/handler/' + id,
    method: 'delete'
  })
}
