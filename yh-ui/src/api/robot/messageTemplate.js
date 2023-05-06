import request from '@/utils/request'

// 查询消息模板列表
export function listMessageTemplate(query) {
  return request({
    url: '/robot/messageTemplate/list',
    method: 'get',
    params: query
  })
}

export function listAll() {
  return request({
    url: '/robot/messageTemplate/listAll',
    method: 'get'
  })
}

// 查询消息模板详细
export function getMessageTemplate(id) {
  return request({
    url: '/robot/messageTemplate/' + id,
    method: 'get'
  })
}

// 新增消息模板
export function addMessageTemplate(data) {
  return request({
    url: '/robot/messageTemplate',
    method: 'post',
    data: data
  })
}

// 修改消息模板
export function updateMessageTemplate(data) {
  return request({
    url: '/robot/messageTemplate',
    method: 'put',
    data: data
  })
}

// 删除消息模板
export function delMessageTemplate(id) {
  return request({
    url: '/robot/messageTemplate/' + id,
    method: 'delete'
  })
}
