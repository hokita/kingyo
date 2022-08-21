import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux'
import type { RootState } from '../../app/rootReducer'

const useAppSelector: TypedUseSelectorHook<RootState> = useSelector

export default useAppSelector
