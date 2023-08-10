import _ from "lodash";
import {
  PUISHMENT_OPERATION_LOCALE_KEY,
  PUISHMENT_REASON_LOCALE_KEY,
} from "../constant/index";

export function getPunishmentOperationText(operation) {
  const key = PUISHMENT_OPERATION_LOCALE_KEY[operation];
  if (_.isNil(key)) {
    return "";
  }

  return this.$t(key);
}

export function getPunishmentReasonText(reason) {
  const key = PUISHMENT_REASON_LOCALE_KEY[reason];
  if (_.isNil(key)) {
    return this.$t("super_admin_punishment_reason_other");
  }

  return this.$t(key);
}
