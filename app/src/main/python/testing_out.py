import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import f1_score, accuracy_score, roc_auc_score, average_precision_score
from sklearn.model_selection import cross_val_score
from sklearn.preprocessing import LabelBinarizer
from sklearn.model_selection import train_test_split
from com.chaquo.python import Python
from os.path import dirname, join


def calculate_values():

    mean_cv_score = 0
    cv_score_std_dev = 0

    filename = join(dirname(__file__), "sample.csv")

    df = pd.read_csv(filename, low_memory=False)
    df_new = df[['KeyNet_1_2', 'KeyNet_1_3', 'KeyNet_1_4', 'KeyNet_1_5', 'KeyNet_1_6', 'KeyNet_1_7']]
    X = df_new[['KeyNet_1_2', 'KeyNet_1_3', 'KeyNet_1_4', 'KeyNet_1_5', 'KeyNet_1_6']]
    # A column vector is being passed instead a 1d array which is expected. Corrected the format.
    y = df_new['KeyNet_1_7']

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.30, random_state=42)
    # def RandomForest(each,X, y,each_sub, num, X_train, X_test, y_test, y_train):

    rf_clf = RandomForestClassifier(n_estimators=100)

    # fit the classifier to the training data
    rf_clf.fit(X_train, y_train)

    # make predictions on the test data
    y_pred = rf_clf.predict(X_test)

    #there is a missing declaration here. What should it be?
    # Initialized LabelBinarizer here to fix the issue
    lb = LabelBinarizer()
    lb.fit(y_train)
    y_pred_one_hot = lb.transform(y_pred)
    y_test_one_hot = lb.transform(y_test)


    if y_test_one_hot.shape[1] != 1:
        roc_score = roc_auc_score(y_test_one_hot, y_pred_one_hot, average='micro', multi_class='ovr')
        acc_score = accuracy_score(y_test_one_hot, y_pred_one_hot)
        f1 = f1_score(y_test_one_hot, y_pred_one_hot, average='micro')
        ap_score = average_precision_score(y_test_one_hot, y_pred_one_hot)
    else:
        roc_score = roc_auc_score(y_test, y_pred)
        acc_score = accuracy_score(y_test, y_pred)
        f1 = f1_score(y_test, y_pred, average='micro')
        ap_score = average_precision_score(y_test, y_pred)
    print("roc score is", roc_score)

    # Calculate and print the mean and standard deviation of the cross-validation scores
    cv_scores = cross_val_score(rf_clf, X, y, cv=5)

    mean_cv_score = cv_scores.mean()
    cv_score_std_dev = cv_scores.std()

    print("Cross-Validation Scores:", cv_scores)
    print("Mean CV Score:", cv_scores.mean())
    print("CV Score Standard Deviation:", cv_scores.std())

    return mean_cv_score,cv_score_std_dev


if __name__ == "__main__":
    mean_cv,std_cv = calculate_values()
    print(f"Mean CV Score: {mean_cv}, CV Score Standard Deviation: {std_cv}")

#the value printed should be shown in the form of a table in your app.
#The table should look as follows
#       Mean   |   SD
#        xxx   | yyyy
#        xxxxx | yyyyy

