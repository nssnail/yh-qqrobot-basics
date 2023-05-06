import request from '@/utils/request'

// 查询好友列列表
export function listFriends(query) {
  return request({
    url: '/robot/friends/list',
    method: 'get',
    params: query
  })
}

// 查询好友列详细
export function getFriends(id) {
  return request({
    url: '/robot/friends/' + id,
    method: 'get'
  })
}

// 新增好友列
export function addFriends(data) {
  return request({
    url: '/robot/friends',
    method: 'post',
    data: data
  })
}

// 修改好友列
export function updateFriends(data) {
  return request({
    url: '/robot/friends',
    method: 'put',
    data: data
  })
}

// 删除好友列
export function delFriends(id) {
  return request({
    url: '/robot/friends/' + id,
    method: 'delete'
  })
}

// 切换状态
export function toggle(id) {
  return request({
    url: '/robot/friends/toggle/' + id,
    method: 'post'
  })
}

// 获取关联列表
export function insertRel(data) {
  return request({
    url: '/robot/friends/insertRel',
    method: 'post',
    data: data
  })
}

// 获取关联列表
export function getRel(data) {
  return request({
    url: '/robot/friends/getRel',
    method: 'post',
    data: data
  })
}
