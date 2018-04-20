from os import cpu_count

import numpy as np
from sklearn.model_selection import GridSearchCV
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler
from sklearn.svm import SVR


class SupportVectorRegression(object):

    @staticmethod
    def predict(X: np.ndarray, y: np.ndarray, x: np.ndarray):
        pipe = make_pipeline(
            StandardScaler(),
            SVR(kernel='rbf', C=1e3, gamma=10)
        )

        grid_search = GridSearchCV(
            pipe,
            param_grid={
                # 'svr__kernel': ['rbf', 'poly'],
                # 'svr__degree': list(range(10)),
                'svr__gamma': [0.1, 0.3, 1, 3, 10, 30],
                'svr__C': [1e3, ]
            },
            n_jobs=cpu_count(),
            verbose=3
        )

        pipe.fit(X, y)

        return pipe.predict(x)