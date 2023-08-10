export const PUISHMENT_OPERATION = {
  REPLACE_ROOM_IMG: 1, //房间封面替换为默认封面
  REPLACE_ROOM_NAME: 2, //房间名替换为默认房间名
  REPLACE_USER_IMG: 3, //头像替换为默认头像
  REPLACE_USERNAME: 4, //替换用户名
  REPLACE_USER_PROFILE: 5, //删除个人简介
  BLOCK_USER_HOUR: 6, //封禁账号1小时
  BLOCK_USER_24_HOUR: 7, //封禁账号24小时
  BLOCK_USER_FOREVER: 8, //永久封禁账号
  BLOCK_FUNCTION_24_HOUR: 9, //封禁功能24小时
  BLOCK_DEVICE: 10, //封禁设备
  BLOCK_ROOM: 11, //封禁房间
};

export const PUISHMENT_OPERATION_LOCALE_KEY = {
  [PUISHMENT_OPERATION.REPLACE_ROOM_IMG]:
    "super_admin_punishment_operation_replace_room_img",
  [PUISHMENT_OPERATION.REPLACE_ROOM_NAME]:
    "super_admin_punishment_operation_replace_room_name",
  [PUISHMENT_OPERATION.REPLACE_USER_IMG]:
    "super_admin_punishment_operation_replace_user_img",
  [PUISHMENT_OPERATION.REPLACE_USERNAME]:
    "super_admin_punishment_operation_replace_user_name",
  [PUISHMENT_OPERATION.REPLACE_USER_PROFILE]:
    "super_admin_punishment_operation_delete_user_profile",
  [PUISHMENT_OPERATION.BLOCK_USER_HOUR]:
    "super_admin_punishment_operation_block_user_1_hour",
  [PUISHMENT_OPERATION.BLOCK_USER_24_HOUR]:
    "super_admin_punishment_operation_block_user_24_hour",
  [PUISHMENT_OPERATION.BLOCK_USER_FOREVER]:
    "super_admin_punishment_operation_block_user_forever",
  [PUISHMENT_OPERATION.BLOCK_FUNCTION_24_HOUR]:
    "super_admin_punishment_operation_block_function_24_hours",
  [PUISHMENT_OPERATION.BLOCK_DEVICE]:
    "super_admin_punishment_operation_block_devide",
  [PUISHMENT_OPERATION.BLOCK_ROOM]:
    "super_admin_punishment_operation_block_room",
};

export const PUISHMENT_REASON = {
  PORN: 0, //色情
  MALICIOUS_INSULTS: 1, //骂人
  SWINDLE: 2, //诈骗
  VIOLENCE_BLOOD_GORE: 3, //血腥暴力
  ADS: 4, //广告
  HARASSMENT: 5, //骚扰
};

export const PUISHMENT_REASON_LOCALE_KEY = {
  [PUISHMENT_REASON.PORN]: "super_admin_punishment_reason_porn",
  [PUISHMENT_REASON.MALICIOUS_INSULTS]:
    "super_admin_punishment_reason_malicious_insults",
  [PUISHMENT_REASON.SWINDLE]: "super_admin_punishment_reason_swindle",
  [PUISHMENT_REASON.VIOLENCE_BLOOD_GORE]:
    "super_admin_punishment_reason_violence_blood_gore",
  [PUISHMENT_REASON.ADS]: "super_admin_punishment_reason_ads",
  [PUISHMENT_REASON.HARASSMENT]: "super_admin_punishment_reason_harassment",
};
