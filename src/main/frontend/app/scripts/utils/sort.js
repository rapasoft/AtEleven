export const ORDER = {
  ASC: (i, j) => i >= j,
  DESC: (i, j) => i < j
};

const sum = (i, j) => i + j.description.length;

export const contentSize = (i) => i.soups.reduce(sum, 0) + i.mainDishes.reduce(sum, 0) + i.other.reduce(sum, 0);

export class Sort {
  static byContentSize(data, order) {
    let sorted = data.sort((i, j) => {
      return order(contentSize(i), contentSize(j));
    });

    let i = 1;
    let j = sorted.length - 1;

    while (i <= j) {
      let tmp = sorted[i];
      sorted[i] = sorted[j];
      sorted[j] = tmp;
      i++;
      j--;
    }

    return sorted;
  }
}
