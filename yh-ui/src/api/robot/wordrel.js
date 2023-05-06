import request from '@/utils/request'

// 查询关键词关联列表
export function listWordrel(query) {
  return request({
    url: '/robot/wordrel/list',
    method: 'get',
    params: query
  })
}

// 查询关键词关联详细
export function getWordrel(id) {
  return request({
    url: '/robot/wordrel/' + id,
    method: 'get'
  })
}

// 新增关键词关联
export function addWordrel(data) {
  return request({
    url: '/robot/wordrel',
    method: 'post',
    data: data
  })
}

// 修改关键词关联
export function updateWordrel(data) {
  return request({
    url: '/robot/wordrel',
    method: 'put',
    data: data
  })
}

// 删除关键词关联
export function delWordrel(id) {
  return request({
    url: '/robot/wordrel/' + id,
    method: 'delete'
  })
}
