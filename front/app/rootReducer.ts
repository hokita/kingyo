import { combineReducers } from 'redux'
import payments from '../features/payments/paymentsSlice'
import store from './store'

const rootReducer = combineReducers({
  payments,
})

export default rootReducer
export type RootState = ReturnType<typeof store.getState>
