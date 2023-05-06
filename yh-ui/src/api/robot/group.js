import request from '@/utils/request'

// 查询群列列表
export function listGroup(query) {
  return request({
    url: '/robot/group/list',
    method: 'get',
    params: query
  })
}

// 查询群列详细
export function getGroup(id) {
  return request({
    url: '/robot/group/' + id,
    method: 'get'
  })
}

// 新增群列
export function addGroup(data) {
  return request({
    url: '/robot/group',
    method: 'post',
    data: data
  })
}

// 修改群列
export function updateGroup(data) {
  return request({
    url: '/robot/group',
    method: 'put',
    data: data
  })
}

// 删除群列
export function delGroup(id) {
  return request({
    url: '/robot/group/' + id,
    method: 'delete'
  })
}

// 切换状态
export function toggle(id) {
  return request({
    url: '/robot/group/toggle/' + id,
    method: 'post'
  })
}

// 获取关联列表
export function insertRel(data) {
  return request({
    url: '/robot/group/insertRel',
    method: 'post',
    data: data
  })
}

// 获取关联列表
export function getRel(data) {
  return request({
    url: '/robot/group/getRel',
    method: 'post',
    data: data
  })
}
