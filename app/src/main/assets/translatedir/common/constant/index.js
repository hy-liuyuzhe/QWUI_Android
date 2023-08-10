export const NAVIGATION_BAR_STYLE = {
  DRAK: 0,
  LIGHT: 1,
};

export const DATE_UNIT = {
  MONTH: 4,
  DAY: 3,
  HOUR: 2,
  MINUTE: 1,
  SECOND: 0,
};

export const MEDIA_TYPE = {
  IMAGE: 0, //图片
  VIDEO: 1, //视频
};

/**
 * 奖品类型
 */
export const AWARD_TYPE = {
  COINS: 0, //金币
  VIP: 1, //vip
  THEME: 2, //主题
  AVATAR_FRAME: 3, //头像框
  CAR: 4, //座驾
  EXP: 5, //经验值
  DIAMOND: 6, //钻石
  VIP1: 7, //VIP1
  VIP2: 8, //VIP2
  VIP3: 9, //VIP3
  VIP4: 10, //VIP4
  GOOD_ID: 12, //靓号
  GIFT: 13, //礼物
  MEDAL: 20,//勋章
};

/**
 * 商品类型
 */
export const GOODS_TYPE = {
  THEME: 0,
  AVATAR_FRAME: 1,
  CAR: 2,
  CUSTOM_THEME: 3,
  RING: 4,
  COINS: 5,
  VIP1: 6,
  VIP2: 7,
  VIP3: 8,
  VIP4: 9,
};

export const ROOM_GLOBAL_CONFIG_TYPE = {
  GLOBAL_ROOM_TEAM_SUPPORT: 24,//房间守护的球队,当数值为空时，表示房间未选择守护的球队,该属性值不允许修改
  GLOBAL_2022_WORLDCUP_TOP8_TEAMS: 25,//2022世界杯8强球队,房间全局配置
  GLOBAL_APPROVING_VERSION: 28//提审版本信息
};

export const FROM_SCENE = {
  IN_ROOM: 0, //房间内场景
  CHAT_MATCH: 1, //1V1匹配场景
  VOICE_MATCH: 2, //语音匹配场景
  COMMON_IM: 3, //普通IM私信场景
  QUICK_SAY_HI: 4, //IM快捷问好
  STARTUP: 5, //app启动
  CHARGE: 6, //支付
  HIGH_POTENTIAL_CHAT: 7, //私信高潜力用户
  ACTIVITY_IM_NOTIFY_FANS: 8, //活动广场中活动通知粉丝
  INITIATIVE_FOLLOW: 9, //用户主动关注
  RECOMMEND_FOLLOW: 10, //用户从推荐关注
  INITIATIVE_ROOM_TEXT: 11, //用户主动发送公屏
  MERCHANT_LIST: 15,//币商列表
  GALA: 16//年度盛典
}

export const FOLLOW_OP_TYPE = {
  UN_FOLLOW: 0,//取消关注
  FOLLOW: 1//关注
}

/**
 * 主播消息类型
 */
export const AnchorMessageType = {
  MESSAGE_WORLDCUP_COIN_CARVEUP_REWARD: 10//世界杯助威榜金币瓜分
}

/**
 * 模板消息类型
 */
export const AnchorMessageTemplateType = {
  NON: 0,
  NORMAL: 1,
  RADIO: 2
}

export const BOARD_ITEM_TYPE = {
  user: 0,//参与榜单排名的是用户ID
  room: 1,//参与榜单排名的是房间ID
  family: 2//参与榜单排名的是家族
}

export const ACTIVITY_CODE = {
  FAMILY_PK_YEARLY: 18,//年度家族争霸
  FAMILY_BOARD: 19//家族榜单活动
};

export const REWARD_STATUS = {
  NO: 0,//没有该奖励的领取权限
  AVAILABLE: 1,//奖励可领取
  TAKEN: 2//奖励已经被领取
}

export const GLOBAL_CONFIG_TYPE = {
  GLOBAL_LUCKYGIFT_SHARING_PERCENT: 27//幸运礼物金币分成
}

export const USER_COMMON_CONFIG_TYPE = {
  AVATAR_FRAME: 0,//头像框
  USER_INVISIBLE_JOIN: 16,//隐身进房
}