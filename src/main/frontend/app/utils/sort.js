export const ORDER = {
  ASC: (i, j) => i >= j,
  DESC: (i, j) => i < j
};

const sum = (i, j) => i + j.description.length;

export const contentSize = (i) => i.soups.reduce(sum, 0) + i.mainDishes.reduce(sum, 0) + i.other.reduce(sum, 0);

export class Sort {
  static byContentSize(data, order) {
    return data.sort((i, j) => {
      return order(contentSize(i), contentSize(j));
    });
  }
}
