export const ACTIVITY_TYPE = {
  GIFT_LOTTERY: 1,
  SUPER_GIFT_LOTTERY: 2,
};
export const RES_CODE = {
  ACTIVITY_INVALID: 900, //无有效活动
  ACTIVITY_TYPE_UN_SUPPORT: 901, //不支持的活动类型
  ACTIVITY_UN_CONFIG: 902, //活动未配置
  ACTIVITY_EXPIRED: 903, //活动已过期
  LOTTERY_UN_GET: 904, //抽奖未中
  LOTTERY_GOODS_GRANT_FAILED: 905, //抽奖奖品发放失败
  SPEND_CURRENCY_FAILED: 906, //消费资产失败
};
export const CURRENCY_TYPE = {
  RAFFLE_TICKET: 2, //代金券
  DEBRIS: 3, //碎片
  LOTTERY_TICKETS: 4, //超级礼物抽奖券
};
export const LOTTERY_TYPE = {
  ONCE: 1, //1次
  TEN_TIMES: 10, //10次
};
export const LOTTERY_PRIZE_TYPE = {
  DEBRIS: 3, //碎片
};
