export const formatDate = (date: Date | string) => {
  const now = new Date();
  const time = new Date(date);
  const timeDifference = now.getTime() - time.getTime();
  const seconds = Math.floor(timeDifference / 1000);
  const minutes = Math.floor(seconds / 60);
  const hours = Math.floor(minutes / 60);
  const days = Math.floor(hours / 24);

  if (days >= 7) {
    const options = { year: "numeric", month: "short", day: "numeric" };
    const formattedDate = time.toLocaleDateString(undefined, options as Intl.DateTimeFormatOptions);
    return formattedDate;
  } else if (days > 0) {
    return `${days} day${days > 1 ? 's' : ''} ago`;
  } else if (hours > 0) {
    return `${hours} hour${hours > 1 ? 's' : ''} ago`;
  } else if (minutes > 0) {
    return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;
  } else {
    return `${seconds} second${seconds > 1 ? 's' : ''} ago`;
  }
}

export const formatString = (category: string): string => {
  return category.toLowerCase().replace(/_/g, ' ');
}
