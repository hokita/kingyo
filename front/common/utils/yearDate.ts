export const formatYearDate = (date: Date): String => {
  const year = date.getFullYear()
  const month = ('0' + (date.getMonth() + 1)).slice(-2)

  return year + month
}

export const previousMonth = (yearDate: String): String => {
  const date = new Date(yearDate.slice(0, 4) + '/' + yearDate.slice(4))
  date.setMonth(date.getMonth() - 1)

  return formatYearDate(date)
}

export const nextMonth = (yearDate: String): String => {
  const date = new Date(yearDate.slice(0, 4) + '/' + yearDate.slice(4))
  date.setMonth(date.getMonth() + 1)

  return formatYearDate(date)
}
